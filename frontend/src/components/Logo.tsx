import { Box, styled, Typography } from '@mui/material'

const LogoText = styled(Typography)({
    fontSize: "1.2rem",
    textAlign: "start",
    display: "inline-block",

})

export const SiteLogo = ({ size, responsive, inverted }: { size?: string, responsive?: boolean, inverted?: boolean }) => {
    return (
        <Box sx={{ userSelect: "none", display: "inline-block" }}>
            <LogoText color={inverted ? 'MenuText' : '#DB5356'} fontSize={size} sx={responsive ? { fontSize: { xs: '0.95rem', md: "1rem", lg: '1.2rem' } } : undefined}>Health</LogoText>
            <LogoText color='MenuText' fontSize={size} sx={responsive ? { fontSize: { xs: '0.95rem', md: "1rem", lg: '1.2rem' } } : undefined}>Sphere</LogoText>
        </Box>
    )
}

export default SiteLogo

export const PageTitle = styled(Typography)({
    fontSize: '1.5rem',
    fontWeight: '600',
    lineHeight: '1.5em'
});

export const PageSubtitle = styled(Typography)({
    fontSize: '1em',
    marginBottom: '16px',
});
